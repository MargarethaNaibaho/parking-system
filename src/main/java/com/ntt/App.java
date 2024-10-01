package com.ntt;

import com.ntt.console.ParkingApp;
import com.ntt.repository.ParkingRepository;
import com.ntt.service.ParkingService;
import com.ntt.utils.InputHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ParkingRepository repository = new ParkingRepository();
        ParkingService service = new ParkingService(repository);

        InputHandler inputHandler = new InputHandler();
        new ParkingApp(service, inputHandler).run();
    }
}
