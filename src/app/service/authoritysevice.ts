import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {User} from "../entity/user";

@Injectable({
  providedIn: 'root'
})
export class AuthoritySevice {

  constructor(private http: HttpClient) {
  }

  async getAutorities(username: string): Promise<string[] | undefined> {
    try {
      const authorities = await this.http.get<string[]>('http://localhost:8080/authorities/' + username).toPromise();
      return authorities;
    } catch (error) {
      console.error(error);
      return undefined;
    }
  }
}
