import { Injectable } from '@angular/core';
import { AuthoritySevice } from './authoritysevice';

@Injectable()
export class AuthorizationManager {

  private readonly localStorageUsreName = 'username';
  private readonly localStorageButtonKey = 'buttonState';
  private readonly localStorageAdmMenus = 'admMenuState';
  private readonly localStorageAcdMenus = 'acdMenuState';
  private readonly localStorageRegMenus = 'regMenuState';
  private readonly localStorageClsMenus = 'clsMenuState';

  public enaadd = false;
  public enaupd = false;
  public enadel = false;

  admMenuItems = [
    { name: 'Employee', accessFlag: true, routerLink: 'employee' },
    { name: 'User', accessFlag: true, routerLink: 'user' },
    { name: 'Privilege', accessFlag: true, routerLink: 'privilege' },
    { name: 'Operation', accessFlag: true, routerLink: 'operation' }
  ];

  acdMenuItems = [
    { name: 'Program', accessFlag: true, routerLink: 'program' },
    { name: 'Course', accessFlag: true, routerLink: 'course' },
    { name: 'Batch', accessFlag: true, routerLink: 'batch' },
    { name: 'Payment Schedule', accessFlag: true, routerLink: 'pymtschedule' },
    { name: 'Course Material', accessFlag: true, routerLink: 'crsmaterial' }
  ];

  regMenuItems = [
    { name: 'Student', accessFlag: true, routerLink: 'students' },
    { name: 'Batch Registration', accessFlag: true, routerLink: 'batchregistration' },
    { name: 'Payments', accessFlag: true, routerLink: 'payments' },
    { name: 'Mat. Distribution', accessFlag: true, routerLink: 'matdistribution' }
  ];

  clsMenuItems = [
    { name: 'Attendance', accessFlag: true, routerLink: 'attendance' },
    { name: 'Class Schedule', accessFlag: true, routerLink: 'clsschedule' },
    { name: 'Progress Review', accessFlag: true, routerLink: 'prgreview' }
  ];


  constructor(private am: AuthoritySevice) {}

  enableButtons(authorities: { module: string; operation: string }[]): void {
    this.enaadd = authorities.some(authority => authority.operation === 'insert');
    this.enaupd = authorities.some(authority => authority.operation === 'update');
    this.enadel = authorities.some(authority => authority.operation === 'delete');

    // Save button state in localStorage
    localStorage.setItem(this.localStorageButtonKey, JSON.stringify({ enaadd: this.enaadd, enaupd: this.enaupd, enadel: this.enadel }));
  }

  enableMenues(modules: { module: string; operation: string }[]): void {
    this.admMenuItems.forEach(menuItem => {
      menuItem.accessFlag = modules.some(module => module.module.toLowerCase() === menuItem.name.toLowerCase());
    });

    this.acdMenuItems.forEach(menuItem => {
      menuItem.accessFlag = modules.some(module => module.module.toLowerCase() === menuItem.name.toLowerCase());
    });

    this.regMenuItems.forEach(menuItem => {
      menuItem.accessFlag = modules.some(module => module.module.toLowerCase() === menuItem.name.toLowerCase());
    });

    this.clsMenuItems.forEach(menuItem => {
      menuItem.accessFlag = modules.some(module => module.module.toLowerCase() === menuItem.name.toLowerCase());
    });

    // Save menu state in localStorage
    localStorage.setItem(this.localStorageAdmMenus, JSON.stringify(this.admMenuItems));
    localStorage.setItem(this.localStorageAcdMenus, JSON.stringify(this.acdMenuItems));
    localStorage.setItem(this.localStorageRegMenus, JSON.stringify(this.regMenuItems));
    localStorage.setItem(this.localStorageClsMenus, JSON.stringify(this.clsMenuItems));

  }


  async getAuth(username: string): Promise<void> {

    this.setUsername(username);

    try {
      const result = await this.am.getAutorities(username);
      if (result !== undefined) {
        const authorities = result.map(authority => {
          const [module, operation] = authority.split('-');
          return { module, operation };
        });

        this.enableButtons(authorities);
        this.enableMenues(authorities);

      } else {
        console.log('Authorities are undefined');
      }
    } catch (error) {
      console.error(error);
    }
  }

  getUsername(): string {
    return localStorage.getItem(this.localStorageUsreName) || '';
  }

  setUsername(value: string): void {
    localStorage.setItem(this.localStorageUsreName, value);
  }

  getEnaAdd(): boolean {
    return this.enaadd;
  }

  getEnaUpd(): boolean {
    return this.enaupd;
  }

  getEnaDel(): boolean {
    return this.enadel;
  }

  initializeButtonState(): void {
    const buttonState = localStorage.getItem(this.localStorageButtonKey);
    if (buttonState) {
      const { enaadd, enaupd, enadel } = JSON.parse(buttonState);
      this.enaadd = enaadd;
      this.enaupd = enaupd;
      this.enadel = enadel;
    }
  }

  initializeMenuState(): void {
    const admMenuState = localStorage.getItem(this.localStorageAdmMenus);
    if (admMenuState) {
      this.admMenuItems = JSON.parse(admMenuState);
    }

    const acdMenuState = localStorage.getItem(this.localStorageAcdMenus);
    if (acdMenuState) {
      this.acdMenuItems = JSON.parse(acdMenuState);
    }

    const regMenuState = localStorage.getItem(this.localStorageRegMenus);
    if (regMenuState) {
      this.regMenuItems = JSON.parse(regMenuState);
    }

    const clsMenuState = localStorage.getItem(this.localStorageClsMenus);
    if (clsMenuState) {
      this.clsMenuItems = JSON.parse(clsMenuState);
    }
  }

  clearUsername(): void {
    localStorage.removeItem(this.localStorageUsreName);
  }

  clearButtonState(): void {
    localStorage.removeItem(this.localStorageButtonKey);
  }

  clearMenuState(): void {
    localStorage.removeItem(this.localStorageAdmMenus);
    localStorage.removeItem(this.localStorageAcdMenus);
    localStorage.removeItem(this.localStorageRegMenus);
    localStorage.removeItem(this.localStorageClsMenus);
  }

  isMenuItemDisabled(menuItem: { accessFlag: boolean }): boolean {
    return !menuItem.accessFlag;
  }

}
