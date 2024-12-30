import { Equipment } from "./equiment";
import { User } from "./user";

export interface UserEquipment{
    id: number,
    user: User,
    equipment: Equipment[],
    assignedDate: Date,
    comments: string
}