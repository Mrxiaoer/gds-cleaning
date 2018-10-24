package com.cloud.dips.admin;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.core.env.StandardEnvironment;

/**
 * @author BigPan
 * <p>
 * 加解密单元测试
 */
public class DipsAdminApplicationTest {
	@Test
	public void testJasypt() {
		// 对应application-dev.yml 中配置的根密码
		System.setProperty("jasypt.encryptor.password", "dips");
		StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());

		//加密方法
		System.out.println(stringEncryptor.encrypt("dips"));

		//解密方法
		System.out.println(stringEncryptor.decrypt("eMVrk40wYpaaFitmQX2jTA=="));
	}
}