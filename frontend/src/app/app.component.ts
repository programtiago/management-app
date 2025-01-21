import { Component } from '@angular/core';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MY_DATE_FORMATS } from './utils/custom-date-format';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  providers: [
    { provide: MAT_DATE_FORMATS, useValue: MY_DATE_FORMATS}
  ]
})
export class AppComponent {

  constructor(){}

  title = 'frontend';

}
