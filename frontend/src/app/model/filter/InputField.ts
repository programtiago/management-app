import { ElementRef } from "@angular/core";
import { MatSelect } from "@angular/material/select";

export interface InputField{
    input: ElementRef | MatSelect, //or its a native element or a mat select (ex: type is a mat select)
    iconKey: string,
    filterProperty: string
}