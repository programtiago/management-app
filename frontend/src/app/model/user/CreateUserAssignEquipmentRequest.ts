import { User } from "./user";

export interface CreateUserAssignEquipmentRequest{
        id: number,
        serialNumber: string,
        description: string,
        brand: string,
        model: string,
        category: string,
        user: User,
        unity: string
}