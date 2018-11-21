package com.cloud.dips.admin.api.vo;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;


/**
 * @author RCG
 * @date 2018-11-19
 */
@Data
public class ImageCode implements Serializable {
	private String code;

	private LocalDateTime expireTime;

	private BufferedImage image;

	public ImageCode(BufferedImage image, String sRand, int defaultImageExpire) {
		this.image = image;
		this.code = sRand;
		this.expireTime = LocalDateTime.now().plusSeconds(defaultImageExpire);
	}
}
