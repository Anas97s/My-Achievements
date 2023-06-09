/*
Compile: make climate_control
Run: ./climate_control
*/

#include "base.h"

/*
Eine Klimaanlage soll bei Temperaturen unter 21 °C heizen, bei 21-23.7 °C nichts tun und bei Temperaturen ab 23.7 °C kühlen. Entwickeln Sie eine Funktion zur Regelung der Klimaanlage, die abhängig von der Temperatur heizt, ausschaltet oder kühlt.
*/

const double HEAT_LIMIT = 21;
const double COOL_LIMIT = 23.7;

enum OperatingMode {
    HEATING,
    OFF,
    COOLING
};

typedef enum OperatingMode OperatingMode; // optional, could use the enum keyword each time

// double -> OperatingMode
// Return the operating mode of the climate control unit given the temperature.
OperatingMode climate_control(double temperature);

static void climate_control_test() {
    test_equal_i(climate_control(17.1), HEATING); // given 17.1 temperature, expected HEATING.
	test_equal_i(climate_control(22.5), OFF); // given 22.5 temperature, expected OFF.
	test_equal_i(climate_control(23.9), COOLING); // given 23.9 temperature, expected COOLING.
	test_equal_i(climate_control(22), OFF); // given 22 temperature, expected OFF.
	test_equal_i(climate_control(15.5), HEATING); // given 15.5 temperature, expected HEATING.
	test_equal_i(climate_control(30.3), COOLING); // given 30.3 temperature, expected COOLING.
    test_equal_i(climate_control(21), OFF); // given 21 temperature, expected OFF.
}

// Return the operating mode of the climate control unit given the temperature.
OperatingMode climate_control(double temperature) {
    if(temperature < HEAT_LIMIT){
		return HEATING;
	} else if(temperature >= HEAT_LIMIT && temperature <= COOL_LIMIT){
		return OFF;
	}else if (temperature > COOL_LIMIT){
		return COOLING;
	}
    return OFF;
}

int main(void) {
    climate_control_test();
    return 0;
}

