import { Equipment } from "../equipment/equiment";
import { User } from "../user/user";

export interface UserEquipment{
    id: number,
    user: User,
    equipment: Equipment[],
    assignedDate: Date,
    comments: string
}