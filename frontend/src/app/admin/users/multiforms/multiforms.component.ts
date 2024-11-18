import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-multiforms',
  templateUrl: './multiforms.component.html',
  styleUrl: './multiforms.component.scss'
})
export class MultiformsComponent implements OnInit{

  constructor(private formBuilder: FormBuilder){}

  ngOnInit(): void {
    
  }

  employeeRegister = this.formBuilder.group({
    basic:this.formBuilder.group({
      firstName: this.formBuilder.control('', Validators.required),
      lastName: this.formBuilder.control('', Validators.required),
      
    }),

    contact: this.formBuilder.group({

    }),

    professionalData: this.formBuilder.group({

    })
  })

}
