package com.observertc.gatekeeper.webrtcstat.repositories;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("jooq")
public class JooqConfiguration {
	//	@Value("${micronaut.application.hikari.jdbc}")
	String dialect;
}