package com.home.user;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;

import org.springframework.test.context.ActiveProfiles;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

import static org.junit.Assert.*;

@ActiveProfiles(value = "test")
@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = UserServiceApplication.class)
@WebAppConfiguration

public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	// Test Objects
	private static User user = null;
	private static User changedUser = null;

	// Mongo DB embedded
	private static MongodExecutable _mongodExe;
	private static MongodProcess _mongod;
	private static MongoClient _mongo;

	@BeforeClass
	public static void beforeAll() throws Exception {
		// setup test data
		user = User.builder().username("user000001").password("password").firstname("First Name User 01")
				.lastname("Last Name User 01").dob(null).build();

		changedUser = User.builder().username("user000001").password("password").firstname("Changed First Name User 01")
				.lastname("Changed Last Name User 01").dob(null).build();

		// set up embedded Mongo DB

		Command command = Command.MongoD;

		IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder().defaults(command)
				.artifactStore(new ExtractedArtifactStoreBuilder().defaults(command)
						.download(new DownloadConfigBuilder().defaultsForCommand(command).build())
						.executableNaming(new UserTempNaming()))
				.build();

		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(Network.getFreeServerPort(), Network.localhostIsIPv6())).build();

		MongodStarter starter = MongodStarter.getInstance(runtimeConfig);

		_mongodExe = starter.prepare(mongodConfig);
		_mongod = _mongodExe.start();
		_mongo = new MongoClient(String.valueOf(Network.localhostIsIPv6()), Network.getFreeServerPort());

	}

	@Before
	public void setup() throws Exception {

		this.repository.deleteAll();

	}

	@AfterClass
	public static void tearDown() throws Exception {

		_mongod.stop();
		_mongodExe.stop();
	}

	@Test
	public void testForInsertUser() {

		User userVerify = this.repository.insert(user);
		assertEquals(userVerify.getUsername(), user.getUsername());

	}

	@Test(expected = org.springframework.dao.DuplicateKeyException.class)
	public void testForDuplicateUser() {

		this.repository.insert(user);
		this.repository.insert(user);

	}

	@Test
	public void testForUpdateUserByUsername() {
		this.repository.insert(user);
		User userVerify = this.repository.updateUserByUsername(user.getUsername(), changedUser);
		long count = this.repository.countByUsername(user.getUsername()).longValue();
		assertEquals(changedUser.getFirstname(), userVerify.getFirstname());
		assertEquals(changedUser.getLastname(), userVerify.getLastname());
		assertEquals(1, count);

	}

	@Test
	public void testForDeleteUser() {
		this.repository.insert(user);
		this.repository.delete(user.getId());
		User userVerify = this.repository.findOne(user.getId());
		assertNull(userVerify);

	}

	public Mongo getMongo() {
		return _mongo;
	}

}
