package com.comunidadedevspace.taskbeats.tasks.domain.use_case

data class TaskListUseCase(
    val insertTaskUseCase: InsertTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val deleteDialogUseCase: DeleteDialogUseCase,
    val deleteAllTasksUseCase: DeleteAllTasksUseCase,
    val deleteAllDialogUseCase: DeleteAllDialogUseCase,
    val getAllTasksUseCase: GetAllTasksUseCase
)