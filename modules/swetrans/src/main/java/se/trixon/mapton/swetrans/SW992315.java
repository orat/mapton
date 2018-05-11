/*
 * Copyright 2018 Patrik Karlström.
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
package se.trixon.mapton.swetrans;

import com.github.goober.coordinatetransformation.positions.SWEREF99Position.SWEREFProjection;
import org.openide.util.lookup.ServiceProvider;
import se.trixon.mapton.core.api.CooTransProvider;
import se.trixon.mapton.core.api.MapBounds;

/**
 *
 * @author Patrik Karlström
 */
@ServiceProvider(service = CooTransProvider.class)
public class SW992315 extends BaseSR {

    public SW992315() {
        mName = "SWEREF 99 23 15";
        mProjection = SWEREFProjection.sweref_99_23_15;
        mBoundsWgs84 = new MapBounds(20.8500, 65.5000, 24.1800, 68.4500);
        mBoundsProjected = new MapBounds(38920.7048, 7267405.2323, 193050.2460, 7597992.2419);
    }
}