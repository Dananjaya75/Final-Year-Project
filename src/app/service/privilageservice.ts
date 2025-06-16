import {Employee} from "../entity/employee";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Gender} from "../entity/gender";
import {Privilege} from "../entity/privilege";
import {Operation} from "../entity/operation";

@Injectable({
  providedIn: 'root'
})

export class Privilageservice {

  constructor(private http: HttpClient) {  }

  async delete(id: number): Promise<[]|undefined>{
    // @ts-ignore
    return this.http.delete('http://localhost:8080/privileges/' + id).toPromise();
  }

  async update(privilage: Privilege): Promise<[]|undefined>{
    return this.http.put<[]>('http://localhost:8080/privileges', privilage).toPromise();
  }


  async getAll(query:string): Promise<Array<Privilege>> {
    const privilages = await this.http.get<Array<Privilege>>('http://localhost:8080/privileges'+query).toPromise();
    if(privilages == undefined){
      return [];
    }
    return privilages;
  }


  async add(privilege: Privilege): Promise<[]|undefined>{
    return this.http.post<[]>('http://localhost:8080/privileges', privilege).toPromise();
  }

  // async getAllByModuleId(id:number): Promise<Array<Operation>> {
  //
  //   const operstions = await this.http.get<Array<Operation>>('http://localhost:8080/privileges/list/' + id).toPromise();
  //   if(operstions == undefined){
  //     return [];
  //   }
  //   return operstions;
  // }


}


