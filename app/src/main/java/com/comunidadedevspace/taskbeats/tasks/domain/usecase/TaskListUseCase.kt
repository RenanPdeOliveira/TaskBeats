package com.comunidadedevspace.taskbeats.tasks.domain.usecase

data class TaskListUseCase(
    val insertTaskUseCase: InsertTaskUseCase,
    val updateTaskUseCase: UpdateTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val deleteDialogUseCase: DeleteDialogUseCase,
    val deleteAllTasksUseCase: DeleteAllTasksUseCase,
    val deleteAllDialogUseCase: DeleteAllDialogUseCase,
    val getAllTasksUseCase: GetAllTasksUseCase
)