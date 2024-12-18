import { User } from "./user";

export interface CreateEquipmentRequest{
    id: number,
    serialNumber: string,
    description: string,
    brand: string,
    model: string,
    category: string,
    unity: string
}