package com.example.controller;

import com.example.dto.TakeActionDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @RequestMapping("/")
    String index() {
        return "index";
    }

    @PostMapping("takeAction")
    public ModelAndView takeAction(@ModelAttribute TakeActionDTO takeActionDTO) {
        ModelAndView mv = new ModelAndView("index");
        System.out.println("sajdasdasdasd");
        List<String[]> listOrders = new ArrayList<>();
        String downloadedData = takeActionDTO.getDownloadedData();
        String packedOrders = takeActionDTO.getPackedOrders();

        for (String line : downloadedData.split("\n")) {
            String[] cells = line.split("\t");
            String trackingNumber = cells[0].trim();
            String SKU = cells[1].trim();
            String[] order = {trackingNumber, SKU};

            listOrders.add(order);
        }

        List<String> listSKUs = new ArrayList<>();
        List<String> unfoundTrackingNumbers = new ArrayList<>();

        for (String eachOrder : packedOrders.split("\n")) {
            boolean existInDownload = false;
            for (String[] order : listOrders) {
                if (eachOrder.trim().equals(order[0])) {
                    if (order[1] != null && !order[1].isEmpty()) {
                        listSKUs.add(order[1]);
                    }
                }
                existInDownload = true;
            }
            if (!existInDownload) {
                unfoundTrackingNumbers.add(eachOrder.trim());
            }
        }

        listSKUs.sort(Comparator.naturalOrder());

        mv.addObject("listSKUsA", listSKUs.stream()
                .filter(SKU -> SKU.startsWith("evaA"))
                .collect(Collectors.joining("\n")));
        mv.addObject("listSKUsQ", listSKUs.stream()
                .filter(SKU -> SKU.startsWith("evaQ"))
                .collect(Collectors.joining("\n")));
        mv.addObject("listSKUsNotFound", listSKUs.stream()
                .filter(SKU -> !SKU.startsWith("evaA") && !SKU.startsWith("evaQ"))
                .collect(Collectors.joining("\n")));
        mv.addObject("unfoundTrackingNumbers", String.join("\n", unfoundTrackingNumbers));
        return mv;
    }
}
