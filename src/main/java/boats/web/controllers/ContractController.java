package boats.web.controllers;

import boats.domain.models.view.*;
import boats.service.interfaces.CharterService;
import boats.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
public class ContractController {
    private final CharterService charterService;

    @Autowired
    public ContractController(CharterService charterService) {
        this.charterService = charterService;
    }


    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Contract")
    public ContractViewModel contract(@PathVariable("id") String charterId){

        ContractViewModel contract = this.charterService.createContract(charterId);

        //todo send to Contract PDF Generator and add boat equipment

        return contract;
    }
}
