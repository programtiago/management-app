import { Equipment } from "./equiment";

export interface EquipmentPage{
    content: Equipment[],
    totalElements: number,
    totalPages: number
}