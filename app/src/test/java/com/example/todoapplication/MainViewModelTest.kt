package com.example.todoapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.todoapplication.data.api.TodoApiServiceInterface
import com.example.todoapplication.data.model.Todo
import com.example.todoapplication.data.repository.MainRepository
import com.example.todoapplication.ui.main.viewmodel.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mockito
import retrofit2.Response
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    @MockK
    lateinit var service: TodoApiServiceInterface


    lateinit var repo: MainRepository

    val testDispatcher = TestCoroutineDispatcher() // swaps the main coroutine dispatcher with testdispatcher as local testing environment doesn't have android main looper

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var observer: Observer<in Boolean>


    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repo = MainRepository(service)
        viewModel = MainViewModel(repo)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()

    }

    @Test
    fun `viewModelTestOnSuccess`() {
        var todoList = arrayListOf<Todo>()
        var todo1 = Todo(1, "Exchange money ", "201802221600", "PENDING")
        var todo2 = Todo(2, "Buying groceries", "201802221600", "PENDING")
        todoList.add(todo1)
        todoList.add(todo2)


        testDispatcher.runBlockingTest {
            coEvery { service.getTodos() } returns todoList
            viewModel.getTodos()
        }
        assertEquals(viewModel.todosList.value, todoList)

    }

    @Test
    fun `liveDataTest`(){

        var todoList = arrayListOf<Todo>()
        var todo1 = Todo(1, "Exchange money ", "201802221600", "PENDING")
        var todo2 = Todo(2, "Buying groceries", "201802221600", "PENDING")
        todoList.add(todo1)
        todoList.add(todo2)

        coEvery { service.getTodos() } returns todoList
        viewModel.getTodos()
        var allTodos = listOf<Todo>()
    /*    var latch = CountDownLatch(1)
        val observer = object : Observer<List<Todo>> {
            override fun onChanged(receivedFromLiveData: List<Todo>?) {
                if (receivedFromLiveData != null) {
                    allTodos = receivedFromLiveData
                }
                latch.countDown()
                viewModel.todosList.removeObserver(this)
            }

        }
        viewModel.todosList.observeForever { observer }
        latch.await(10,TimeUnit.SECONDS)*/
        allTodos = viewModel.todosList.getOrAwaitValue()
        assertNotNull(allTodos)
        assertTrue(allTodos.isNotEmpty())
        Assert.assertEquals(todoList, allTodos)
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}