/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.hackingspringboot.reactive;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author freeefly
 */
class BlockHoundUnitTest {

	// tag::obvious-failure[]
	@Test
	void threadSleepIsABlockingCall() {
		Mono.delay(Duration.ofSeconds(1)) // <1>
				.flatMap(tick -> {
					try {
						Thread.sleep(10); // <2> Blocking Call!
						return Mono.just(true);
					} catch (InterruptedException e) {
						return Mono.error(e);
					}
				}) //
				.as(StepVerifier::create) //
//				.verifyComplete();
				.expectErrorMatches(throwable -> {
					assertThat(throwable.getMessage()) //
							.contains("Blocking call! java.lang.Thread.sleep");
					return true;
				});

	}
	// end::obvious-failure[]

}
