package com.example.in2000_team1.datalayer

class ApplianceCalc {

    // Calculates the cost of using an appliance with a given price
    fun calculateApplianceCost(appliance: Appliance, pricePerKwh: Double): Double {
        val kilowattHours = appliance.usageWatts * (appliance.durationMinutes / 60.0) / 1000.0
        return kilowattHours * pricePerKwh
    }
}