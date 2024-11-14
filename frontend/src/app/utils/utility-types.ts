import { FormControl, FormGroup } from "@angular/forms";

export type ModelFormGroup<T> = FormGroup<{
    [K in keyof T]: FormControl<T[K]>;
}>;