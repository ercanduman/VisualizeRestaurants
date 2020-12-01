package ercanduman.visualizerestaurant

import androidx.test.platform.app.InstrumentationRegistry
import ercanduman.visualizerestaurant.data.db.AppDatabaseTest
import ercanduman.visualizerestaurant.ui.main.MainActivityTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * Instrumented test, which will execute on an Android device.
 *
 * Test Suite combines all tests and gives ability to run all test cases with single click.
 *
 * When you run this class (UITestSuite.class), it will run all the tests in all the suite classes.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    AppDatabaseTest::class
)
class UITestSuite {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ercanduman.visualizerestaurant", appContext.packageName)
    }
}