package applango.common.SuiteAndCategory;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

@RunWith(Categories.class)
@Categories.IncludeCategory(IntegrationTestsCategory.class)
//@Suite.SuiteClasses({dashboardTests.class

//})
public class IntegrationsTestSuite {
}
