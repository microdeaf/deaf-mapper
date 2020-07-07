/*
 * Copyright 2013-2020 the original author or authors.
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
package org.microdeaf.mapper.enums;

import org.microdeaf.mapper.annotation.MicrodeafMapper;

/**
 * This enum specified type of target class
 * that defined in {@link MicrodeafMapper}
 *
 * @author Mohammad Khosrojerdi     m.khosrojerdi.d@gmail.com
 * @since 2020-07-06
 */
public enum ClassType {

    /**
     * Entity declaration
     */
    ENTITY,

    /**
     * View declaration
     */
    VIEW;

}
