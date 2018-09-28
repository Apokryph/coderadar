package org.wickedsource.coderadar.score.rest.scoreprofile;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.wickedsource.coderadar.testframework.category.ControllerTest;
import org.wickedsource.coderadar.testframework.template.ControllerTestTemplate;

import static org.wickedsource.coderadar.factories.databases.DbUnitFactory.Projects.SINGLE_PROJECT;

@Category(ControllerTest.class)
public class ScoreProfileControllerTest extends ControllerTestTemplate {

    @Test
    public void createScoreProfile() {
        System.out.print("ScoreProfileTest");
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    public void updateScoreProfile() {
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    public void getScoreProfile() {
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    public void deleteScoreProfile() {
    }

    @Test
    @DatabaseSetup(SINGLE_PROJECT)
    public void listScoreProfiles() {
    }
}