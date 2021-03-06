/*
 * Copyright 2019 Patrik Karlström.
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
package org.mapton.demo.ww;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Box;
import gov.nasa.worldwind.render.Cone;
import gov.nasa.worldwind.render.Cylinder;
import gov.nasa.worldwind.render.Ellipsoid;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Pyramid;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.Wedge;
import java.util.ArrayList;
import org.mapton.worldwind.api.LayerBundle;

/**
 *
 * @author Patrik Karlström
 */
@org.openide.util.lookup.ServiceProvider(service = LayerBundle.class)
public class RigidShapesLayerBundle extends LayerBundle {

    private final RenderableLayer mRigidShapesLayer = new RenderableLayer();
    private final RenderableLayer mCylinderLayer = new RenderableLayer();

    public RigidShapesLayerBundle() {
        mRigidShapesLayer.setName("Rigid Shapes");
        mRigidShapesLayer.setEnabled(false);
    }

    public RenderableLayer getLayer() {
        return mRigidShapesLayer;
    }

    @Override
    public void populate() throws Exception {
        addCylinders();
//        addRigidShapes();
        getLayers().setAll(mRigidShapesLayer, mCylinderLayer);
        setPopulated(true);
    }

    private void addCylinders() {
        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setInteriorMaterial(Material.YELLOW);
        attrs.setInteriorOpacity(0.7);
        attrs.setEnableLighting(true);
        attrs.setOutlineMaterial(Material.RED);
        attrs.setOutlineWidth(2d);
        attrs.setDrawInterior(true);
        attrs.setDrawOutline(false);

        // Create and set an attribute bundle.
        ShapeAttributes attrs2 = new BasicShapeAttributes();
        attrs2.setInteriorMaterial(Material.PINK);
        attrs2.setInteriorOpacity(1);
        attrs2.setEnableLighting(true);
        attrs2.setOutlineMaterial(Material.WHITE);
        attrs2.setOutlineWidth(2d);
        attrs2.setDrawOutline(false);

        // ********* sample  Cylinders  *******************
        // Cylinder with equal axes, ABSOLUTE altitude mode
        Cylinder cylinder3 = new Cylinder(Position.fromDegrees(40, -120, 80000), 100000, 50000);
        cylinder3.setAltitudeMode(WorldWind.ABSOLUTE);
        cylinder3.setAttributes(attrs);
        cylinder3.setVisible(true);
        cylinder3.setValue(AVKey.DISPLAY_NAME, "Cylinder with equal axes, ABSOLUTE altitude mode");
        mCylinderLayer.addRenderable(cylinder3);

        // Cylinder with equal axes, RELATIVE_TO_GROUND
        Cylinder cylinder4 = new Cylinder(Position.fromDegrees(37.5, -115, 50000), 50000, 50000, 50000);
        cylinder4.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder4.setAttributes(attrs);
        cylinder4.setVisible(true);
        cylinder4.setValue(AVKey.DISPLAY_NAME, "Cylinder with equal axes, RELATIVE_TO_GROUND altitude mode");
        mCylinderLayer.addRenderable(cylinder4);

        // Cylinder with equal axes, CLAMP_TO_GROUND
        Cylinder cylinder5 = new Cylinder(Position.fromDegrees(35, -110, 50000), 50000, 50000, 50000);
        cylinder5.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        cylinder5.setAttributes(attrs);
        cylinder5.setVisible(true);
        cylinder5.setValue(AVKey.DISPLAY_NAME, "Cylinder with equal axes, CLAMP_TO_GROUND altitude mode");
        mCylinderLayer.addRenderable(cylinder5);

        // Cylinder with a texture, using Cylinder(position, height, radius) constructor
        Cylinder cylinder9 = new Cylinder(Position.fromDegrees(0, -90, 600000), 1200000, 600000);
        cylinder9.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder9.setImageSources("gov/nasa/worldwindx/examples/images/500px-Checkerboard_pattern.png");
        cylinder9.setAttributes(attrs);
        cylinder9.setVisible(true);
        cylinder9.setValue(AVKey.DISPLAY_NAME, "Cylinder with a texture");
        mCylinderLayer.addRenderable(cylinder9);

        // Scaled Cylinder with default orientation
        Cylinder cylinder = new Cylinder(Position.ZERO, 1000000, 500000, 100000);
        cylinder.setAltitudeMode(WorldWind.ABSOLUTE);
        cylinder.setAttributes(attrs);
        cylinder.setVisible(true);
        cylinder.setValue(AVKey.DISPLAY_NAME, "Scaled Cylinder with default orientation");
        mCylinderLayer.addRenderable(cylinder);

        // Scaled Cylinder with a pre-set orientation
        Cylinder cylinder2 = new Cylinder(Position.fromDegrees(0, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        cylinder2.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder2.setAttributes(attrs2);
        cylinder2.setValue(AVKey.DISPLAY_NAME, "Scaled Cylinder with a pre-set orientation");
        cylinder2.setVisible(true);

        mCylinderLayer.addRenderable(cylinder2);

        // Scaled Cylinder with a pre-set orientation
        Cylinder cylinder6 = new Cylinder(Position.fromDegrees(30, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        cylinder6.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder6.setImageSources("gov/nasa/worldwindx/examples/images/500px-Checkerboard_pattern.png");
        cylinder6.setAttributes(attrs2);
        cylinder6.setVisible(true);
        cylinder6.setValue(AVKey.DISPLAY_NAME, "Scaled Cylinder with a pre-set orientation");
        mCylinderLayer.addRenderable(cylinder6);

        // Scaled Cylinder with a pre-set orientation
        Cylinder cylinder7 = new Cylinder(Position.fromDegrees(60, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        cylinder7.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder7.setAttributes(attrs2);
        cylinder7.setVisible(true);
        cylinder7.setValue(AVKey.DISPLAY_NAME, "Scaled Cylinder with a pre-set orientation");
        mCylinderLayer.addRenderable(cylinder7);

        // Scaled, oriented Cylinder in 3rd "quadrant" (-X, -Y, -Z)
        Cylinder cylinder8 = new Cylinder(Position.fromDegrees(-45, -180, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        cylinder8.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder8.setAttributes(attrs2);
        cylinder8.setVisible(true);
        cylinder8.setValue(AVKey.DISPLAY_NAME, "Scaled, oriented Cylinder in the 3rd 'quadrant' (-X, -Y, -Z)");
        mCylinderLayer.addRenderable(cylinder8);
    }

    private void addRigidShapes() {
        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setInteriorMaterial(Material.YELLOW);
        attrs.setInteriorOpacity(0.7);
        attrs.setEnableLighting(true);
        attrs.setOutlineMaterial(Material.RED);
        attrs.setOutlineWidth(2d);
        attrs.setDrawInterior(true);
        attrs.setDrawOutline(false);

        // Create and set a second attribute bundle.
        ShapeAttributes attrs2 = new BasicShapeAttributes();
        attrs2.setInteriorMaterial(Material.PINK);
        attrs2.setInteriorOpacity(1);
        attrs2.setEnableLighting(true);
        attrs2.setOutlineMaterial(Material.WHITE);
        attrs2.setOutlineWidth(2d);
        attrs2.setDrawOutline(false);

        // Pyramid with equal axes, ABSOLUTE altitude mode.
        Pyramid pyramid = new Pyramid(Position.fromDegrees(40, -120, 220000), 200000, 200000, 200000);
        pyramid.setAltitudeMode(WorldWind.ABSOLUTE);
        pyramid.setAttributes(attrs);
        pyramid.setValue(AVKey.DISPLAY_NAME, "Pyramid with equal axes, ABSOLUTE altitude mode");
        mRigidShapesLayer.addRenderable(pyramid);

        // Cone with equal axes, RELATIVE_TO_GROUND.
        Cone cone = new Cone(Position.fromDegrees(37.5, -115, 200000), 200000, 200000, 200000);
        cone.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cone.setAttributes(attrs);
        cone.setValue(AVKey.DISPLAY_NAME, "Cone with equal axes, RELATIVE_TO_GROUND altitude mode");
        mRigidShapesLayer.addRenderable(cone);

        // Wedge with equal axes, CLAMP_TO_GROUND.
        Wedge wedge = new Wedge(Position.fromDegrees(35, -110, 200000), Angle.fromDegrees(225),
                200000, 200000, 200000);
        wedge.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
        wedge.setAttributes(attrs);
        wedge.setValue(AVKey.DISPLAY_NAME, "Wedge with equal axes, CLAMP_TO_GROUND altitude mode");
        mRigidShapesLayer.addRenderable(wedge);

        // Box with a texture.
        Box box = new Box(Position.fromDegrees(0, -90, 600000), 600000, 600000, 600000);
        box.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ArrayList<Object> imageSources = new ArrayList<>();
        imageSources.add("images/32x32-icon-nasa.png");
        imageSources.add(null);
        imageSources.add("org/mapton/demo/500px-Checkerboard_pattern.png");
        imageSources.add(null);
        imageSources.add("images/64x64-crosshair.png");
        imageSources.add(null);
        box.setImageSources(imageSources);
        box.setAttributes(attrs);
        box.setValue(AVKey.DISPLAY_NAME, "Box with a texture");
        mRigidShapesLayer.addRenderable(box);

        // Sphere with a texture.
        Ellipsoid sphere = new Ellipsoid(Position.fromDegrees(0, -110, 600000), 600000, 600000, 600000);
        sphere.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        sphere.setImageSources("org/mapton/demo/500px-Checkerboard_pattern.png");
        sphere.setAttributes(attrs);
        sphere.setValue(AVKey.DISPLAY_NAME, "Sphere with a texture");
        mRigidShapesLayer.addRenderable(sphere);

        // Cylinder with a texture.
        Cylinder cylinder = new Cylinder(Position.fromDegrees(0, -130, 600000), 600000, 600000, 600000);
        cylinder.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        cylinder.setImageSources("org/mapton/demo/500px-Checkerboard_pattern.png");
        cylinder.setAttributes(attrs);
        cylinder.setValue(AVKey.DISPLAY_NAME, "Cylinder with a texture");
        mRigidShapesLayer.addRenderable(cylinder);

        // Cylinder with default orientation.
        cylinder = new Cylinder(Position.ZERO, 600000, 500000, 300000);
        cylinder.setAltitudeMode(WorldWind.ABSOLUTE);
        cylinder.setAttributes(attrs);
        cylinder.setValue(AVKey.DISPLAY_NAME, "Cylinder with default orientation");
        mRigidShapesLayer.addRenderable(cylinder);

        // Ellipsoid with a pre-set orientation.
        Ellipsoid ellipsoid = new Ellipsoid(Position.fromDegrees(0, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        ellipsoid.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ellipsoid.setAttributes(attrs2);
        ellipsoid.setValue(AVKey.DISPLAY_NAME, "Ellipsoid with a pre-set orientation");
        mRigidShapesLayer.addRenderable(ellipsoid);

        // Ellipsoid with a pre-set orientation.
        ellipsoid = new Ellipsoid(Position.fromDegrees(30, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        ellipsoid.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ellipsoid.setImageSources("org/mapton/demo/500px-Checkerboard_pattern.png");
        ellipsoid.setAttributes(attrs2);
        ellipsoid.setValue(AVKey.DISPLAY_NAME, "Ellipsoid with a pre-set orientation");
        mRigidShapesLayer.addRenderable(ellipsoid);

        // Ellipsoid with a pre-set orientation.
        ellipsoid = new Ellipsoid(Position.fromDegrees(60, 30, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        ellipsoid.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ellipsoid.setAttributes(attrs2);
        ellipsoid.setValue(AVKey.DISPLAY_NAME, "Ellipsoid with a pre-set orientation");
        mRigidShapesLayer.addRenderable(ellipsoid);

        // Ellipsoid oriented in 3rd "quadrant" (-X, -Y, -Z).
        ellipsoid = new Ellipsoid(Position.fromDegrees(-45, -180, 750000), 1000000, 500000, 100000,
                Angle.fromDegrees(90), Angle.fromDegrees(45), Angle.fromDegrees(30));
        ellipsoid.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ellipsoid.setAttributes(attrs2);
        ellipsoid.setValue(AVKey.DISPLAY_NAME, "Ellipsoid oriented in 3rd \"quadrant\" (-X, -Y, -Z)");
        mRigidShapesLayer.addRenderable(ellipsoid);
    }

}
