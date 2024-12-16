import { User } from "./user";

export interface CreateEquipmentAssignUserRequest{
    id: number,
    description: string,
    brand: string,
    model: string,
    category: string,
    user: User,
    unity: string
}