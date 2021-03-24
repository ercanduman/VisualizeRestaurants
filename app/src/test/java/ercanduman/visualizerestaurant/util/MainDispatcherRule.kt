package ercanduman.visualizerestaurant.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Provides coroutine rule for suspend executions.
 *
 * Main dispatcher is needed for unit test cases, if not provided then following error will be
 * thrown: "Module with the Main dispatcher had failed to initialize. For tests
 *  Dispatchers.setMain from kotlinx-coroutines-test module can be used"
 *
 * @author ercan
 * @since  2/17/21
 */
class MainDispatcherRule(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}