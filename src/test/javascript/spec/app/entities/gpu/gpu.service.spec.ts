/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import GpuService from '@/entities/gpu/gpu.service';
import { Gpu } from '@/shared/model/gpu.model';
import { BusType } from '@/shared/model/enumerations/bus-type.model';

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
  describe('Gpu Service', () => {
    let service: GpuService;
    let elemDefault;

    beforeEach(() => {
      service = new GpuService();
      elemDefault = new Gpu(123, 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', BusType.PCI);
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

      it('should create a Gpu', async () => {
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

      it('should not create a Gpu', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Gpu', async () => {
        const returnedFromService = Object.assign(
          {
            frequency: 1,
            memory: 1,
            consumption: 1,
            clockSpeed: 1,
            lithography: 1,
            output: 'BBBBBB',
            inputPower: 'BBBBBB',
            bus: 'BBBBBB',
            dimension: {
              height: 15,
              width: 15,
              length: 15,
            },
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Gpu', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Gpu', async () => {
        const patchObject = Object.assign(
          {
            frequency: 1,
            memory: 1,
            consumption: 1,
            lithography: 1,
            inputPower: 'BBBBBB',
            bus: 'BBBBBB',
            dimension: {
              height: 15,
              width: 15,
              length: 15,
            },
          },
          new Gpu()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Gpu', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Gpu', async () => {
        const returnedFromService = Object.assign(
          {
            frequency: 1,
            memory: 1,
            consumption: 1,
            clockSpeed: 1,
            lithography: 1,
            output: 'BBBBBB',
            inputPower: 'BBBBBB',
            bus: 'BBBBBB',
            dimension: {
              height: 15,
              width: 15,
              length: 15,
            },
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Gpu', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Gpu', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Gpu', async () => {
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
