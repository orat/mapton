/*
 * Copyright 2020 Patrik Karlström.
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
package org.mapton.api.report;

import javafx.scene.Node;

/**
 *
 * @author Patrik Karlström
 */
public interface MSplitNavType {

    public String getName();

    public Node getNode();

    public String getParent();

    public MSplitNavSettings getSplitNavSettings();

    default public void onSelect() {
    }

    public void setName(String name);

    public void setParent(String parent);

}
