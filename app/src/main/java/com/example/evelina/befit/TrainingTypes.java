package com.example.evelina.befit;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

/**
 * Created by User on 04-Oct-16.
 */

/**
 * Class for holding the names of the completed challenges.Needed for expandable recycler view.
 */
public class TrainingTypes implements ParentListItem {
    private List trainingSpecifications;
    private String name;

    public TrainingTypes(List trainingSpecifications,String name) {
        this.trainingSpecifications = trainingSpecifications;
        this.name = name;
    }

    @Override
    public List<?> getChildItemList() {
        return trainingSpecifications;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return this.name;
    }
}
