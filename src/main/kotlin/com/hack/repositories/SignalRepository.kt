package com.hack.repositories

import com.hack.domain.Signal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SignalRepository: JpaRepository<Signal, Long>