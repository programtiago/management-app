import { User } from "../../user/user";
import { Department } from "../department";

export interface UserDepartment{
    id: number,
    user: User,
    department: Department,
    assignedDate: Date,
    comments: string
}