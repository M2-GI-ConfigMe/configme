/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import VentiradService from '@/entities/ventirad/ventirad.service';
import { Ventirad } from '@/shared/model/ventirad.model';

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
  describe('Ventirad Service', () => {
    let service: VentiradService;
    let elemDefault;

    beforeEach(() => {
      service = new VentiradService();
      elemDefault = new Ventirad(123, 'AAAAAA', 0.5, 0.3, 27, 'fsdf', 'INTEL', true, ['LGA_1151', 'LGA_1200'], 'AAAAAAA', 0, false);
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

      it('should create a Ventirad', async () => {
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

      it('should not create a Ventirad', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Ventirad', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            discount: 0.3,
            stock: 27,
            img: 'fsdf',
            brand: 'INTEL',
            rangeFanSpeed: 'BBBBBB',
            sockets: ['LGA_1151', 'LGA_1200'],
            noise: 1,
            hasThermalPaste: true,
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

      it('should not update a Ventirad', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Ventirad', async () => {
        const patchObject = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            stock: 27,
            brand: 'INTEL',
            isActive: false,
            sockets: ['LGA_1151', 'LGA_1200'],
            noise: 1,
            hasThermalPaste: true,
            dimension: {
              height: 15,
              width: 15,
              length: 15,
            },
          },
          new Ventirad()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Ventirad', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Ventirad', async () => {
        const returnedFromService = Object.assign(
          {
            rangeFanSpeed: 'BBBBBB',
            noise: 1,
            hasThermalPaste: true,
            sockets: ['LGA_1151', 'LGA_1200'],
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

      it('should not return a list of Ventirad', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Ventirad', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Ventirad', async () => {
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
