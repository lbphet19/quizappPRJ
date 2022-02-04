export class User {
  id:number
  username:string
  name:string
  roles:string[]
  email:string
  constructor(id:any,username:any,name:any,roles:any,email:any){
    this.id = id,
    this.username = username,
    this.name = name,
    this.roles = roles,
    this.email = email
  }
}
