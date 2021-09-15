/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import CpuService from '@/entities/cpu/cpu.service';
import { Cpu } from '@/shared/model/cpu.model';
import { SocketType } from '@/shared/model/enumerations/socket-type.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Cpu Service', () => {
    let service: CpuService;
    let elemDefault;

    beforeEach(() => {
      service = new CpuService();
      elemDefault = new Cpu(123, 'AAAAAA', 0.5, 0.3, 27, 'fsdf', 'INTEL', true, 0, 0, 0, 0, 0, 0, false, SocketType.AM4, 0, 0, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Cpu', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Cpu', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Cpu', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            discount: 0.3,
            stock: 27,
            img: 'fsdf',
            brand: 'INTEL',
            isActive: false,
            frequency: 1,
            cacheL1: 1,
            cacheL2: 1,
            cacheL3: 1,
            nbHeart: 1,
            nbThread: 1,
            hasVentirad: true,
            socketType: 'BBBBBB',
            lithography: 1,
            ramFrequencyMax: 1,
            consumption: 1,
            hasGpu: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Cpu', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Cpu', async () => {
        const patchObject = Object.assign(
          {
            nbHeart: 1,
            ramFrequencyMax: 1,
            consumption: 1,
          },
          new Cpu()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Cpu', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Cpu', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            stock: 27,
            brand: 'INTEL',
            frequency: 1,
            cacheL1: 1,
            cacheL2: 1,
            cacheL3: 1,
            nbHeart: 1,
            nbThread: 1,
            hasVentirad: true,
            socketType: 'BBBBBB',
            lithography: 1,
            ramFrequencyMax: 1,
            consumption: 1,
            hasGpu: true,
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Cpu', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Cpu', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Cpu', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
