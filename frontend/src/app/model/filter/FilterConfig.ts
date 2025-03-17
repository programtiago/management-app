import { Observable } from "rxjs";
import { Equipment } from "../equipment/equiment";

export type FilterOperation = 'All' | 'Contains' | 'Starts With' | 'Equals';

export type DateFilterOperation = 'All' | 'Equals' | 'Greater Than' | 'Less Than' | 'Greater than or Equal' | 'Less than or Equal'

export interface FilterConfig {
    icon: string,
    operation: FilterOperation,
    filterFn: (value: string) => Observable<Equipment[]>
}

