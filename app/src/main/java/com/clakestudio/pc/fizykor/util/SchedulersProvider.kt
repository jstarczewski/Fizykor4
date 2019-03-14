package com.clakestudio.pc.fizykor.util

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun uiScheduler(): Scheduler
    fun ioScheduler(): Scheduler

}