package applango.common.SuiteAndCategory;


import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(dashboardUITestsCategory.class)
@Suite.SuiteClasses({dashboardUITestSuite.class})
public class dashboardUITestSuite {
}
