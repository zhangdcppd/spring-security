/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.web.headers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests for the {@code HeadersFilter}
 *
 * @author Marten Deinum
 * @since 3.2
 */
@RunWith(MockitoJUnitRunner.class)
public class HeadersFilterTest {
    @Mock
    private HeaderWriter writer1;

    @Mock
    private HeaderWriter writer2;

    @Test
    public void noHeadersConfigured() throws Exception {
        List<HeaderWriter> headerWriters = new ArrayList<HeaderWriter>();
        HeadersFilter filter = new HeadersFilter(headerWriters);
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        assertTrue(response.getHeaderNames().isEmpty());
    }

    @Test
    public void additionalHeadersShouldBeAddedToTheResponse() throws Exception {
        List<HeaderWriter> headerWriters = new ArrayList<HeaderWriter>();
        headerWriters.add(writer1);
        headerWriters.add(writer2);

        HeadersFilter filter = new HeadersFilter(headerWriters);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        filter.doFilter(request, response, filterChain);

        verify(writer1).writeHeaders(request, response);
        verify(writer2).writeHeaders(request, response);
    }
}
