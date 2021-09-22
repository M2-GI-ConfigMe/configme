import axios from 'axios';

import { IHardDrive } from '@/shared/model/hard-drive.model';

const baseApiUrl = 'api/hard-drives';

export default class HardDriveService {
  public find(id: number): Promise<IHardDrive> {
    return new Promise<IHardDrive>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(page?: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl, { params: { page } })
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IHardDrive): Promise<IHardDrive> {
    return new Promise<IHardDrive>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IHardDrive): Promise<IHardDrive> {
    return new Promise<IHardDrive>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IHardDrive): Promise<IHardDrive> {
    return new Promise<IHardDrive>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
