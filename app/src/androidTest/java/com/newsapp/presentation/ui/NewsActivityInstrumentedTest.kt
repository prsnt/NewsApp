import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.newsapp.presentation.ui.NewsActivity
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsActivityInstrumentedTest {

    // Create an instance of ActivityScenario for NewsActivity
    private lateinit var activityScenario: ActivityScenario<NewsActivity>

    // Use createAndroidComposeRule to launch NewsActivity with Compose content
    @get:Rule
    val composeTestRule = createAndroidComposeRule<NewsActivity>()

    @Test
    fun testCategorySelectionAndNewsList() {
        // Launch NewsActivity
        activityScenario = ActivityScenario.launch(NewsActivity::class.java)

        // Perform UI interactions and assertions using Espresso and Compose UI testing
        composeTestRule.apply {
            // Verify initial state of NewsActivity
            onNodeWithText("Business").assertExists()

            // Perform click on a category (e.g., "Sports")
            onNodeWithText("Sports").performClick()

            // Wait for news to load (you may need to introduce a delay or use synchronization)
            Thread.sleep(1000) // Example delay, replace with proper synchronization

            // Verify that news articles are displayed
            onNodeWithText("No data found").assertDoesNotExist() // Assuming news data is loaded

            // Perform more assertions as needed
            // Example:
            // onNodeWithText("Title of an article").assertExists()
        }
    }

    @Test
    fun testCategorySelectionAndLoadingIndicator() {
        // Launch NewsActivity
        activityScenario = ActivityScenario.launch(NewsActivity::class.java)

        // Perform UI interactions and assertions using Espresso and Compose UI testing
        composeTestRule.apply {
            // Verify initial state of NewsActivity
            onNodeWithText("Business").assertExists()

            // Perform click on a category (e.g., "Technology")
            onNodeWithText("Technology").performClick()

            // Verify loading indicator is displayed
            onNodeWithText("No data found").assertDoesNotExist()
            onNodeWithContentDescription("Loading...").assertExists() // Example loading indicator

            // Wait for news to load (you may need to introduce a delay or use synchronization)
            Thread.sleep(1000) // Example delay, replace with proper synchronization

            // Verify loading indicator is gone and news articles are displayed
            onNodeWithContentDescription("Loading...").assertDoesNotExist()
            onNodeWithText("No data found").assertDoesNotExist() // Assuming news data is loaded

            // Perform more assertions as needed
            // Example:
            // onNodeWithText("Title of an article").assertExists()
        }
    }

    @Test
    fun testScrollNewsList() {
        // Launch NewsActivity
        activityScenario = ActivityScenario.launch(NewsActivity::class.java)

        // Perform UI interactions and assertions using Espresso and Compose UI testing
        composeTestRule.apply {
            // Verify initial state of NewsActivity
            onNodeWithText("Business").assertExists()

            // Perform click on a category (e.g., "General")
            onNodeWithText("General").performClick()

            // Wait for news to load (you may need to introduce a delay or use synchronization)
            Thread.sleep(1000) // Example delay, replace with proper synchronization

            // Scroll through the news list
            //onView(ViewMatchers.withId(R.id.news_list)).perform(ViewActions.swipeUp())

            // Perform more assertions as needed
            // Example:
            // onNodeWithText("Title of an article").assertExists()
        }
    }

    // Clean up after each test
    @After
    fun tearDown() {
        activityScenario.close()
    }
}
