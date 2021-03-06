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
package org.mapton.core.ui.bookmark;

import javafx.scene.Scene;
import org.mapton.base.ui.bookmark.CategoryView;
import se.trixon.almond.nbp.fx.FxDialogPanel;

/**
 *
 * @author Patrik Karlström
 */
public class CategoryPanel extends FxDialogPanel {

    private CategoryView mCategoryView;

    public String getCategory() {
        return mCategoryView.getCategory();
    }

    public void setCategory(String category) {
        mCategoryView.setCategory(category);
    }

    @Override
    protected void fxConstructor() {
        mCategoryView = new CategoryView();
        setScene(new Scene(mCategoryView));
    }
}
