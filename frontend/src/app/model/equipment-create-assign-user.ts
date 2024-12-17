import { User } from "./user";

export interface CreateEquipmentAssignUserRequest{
    id: number,
    serialNumber: string,
    description: string,
    brand: string,
    model: string,
    category: string,
    user: User,
    unity: string
}