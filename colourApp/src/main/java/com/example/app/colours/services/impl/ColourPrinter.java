package com.example.app.colours.services.impl;

import com.example.app.colours.services.BluePrinter;
import com.example.app.colours.services.GreenPrinter;
import com.example.app.colours.services.RedPrinter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class ColourPrinter implements com.example.app.colours.services.ColourPrinter {

    private RedPrinter redPrinter;
    private GreenPrinter greenPrinter;
    private BluePrinter bluePrinter;

    @Override
    public String print() {
        return String.join(", ", redPrinter.print(), greenPrinter.print(), bluePrinter.print());
    }
}
