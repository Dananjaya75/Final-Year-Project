import {Designation} from "../entity/designation";
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})

export class DesignationService {

  constructor(private http: HttpClient) {  }

  async getAllList(): Promise<Array<Designation>> {

    const designations = await this.http.get<Array<Designation>>('http://localhost:8080/designations/list').toPromise();
    if(designations == undefined){
      return [];
    }
    return designations;
  }

}


