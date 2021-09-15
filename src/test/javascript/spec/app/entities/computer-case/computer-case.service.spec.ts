/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import ComputerCaseService from '@/entities/computer-case/computer-case.service';
import { ComputerCase } from '@/shared/model/computer-case.model';
import { CaseType } from '@/shared/model/enumerations/case-type.model';

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
  describe('ComputerCase Service', () => {
    let service: ComputerCaseService;
    let elemDefault;

    beforeEach(() => {
      service = new ComputerCaseService();
      elemDefault = new ComputerCase(
        123,
        'AAAAAA',
        0.5,
        0.3,
        27,
        'fsdf',
        'INTEL',
        true,
        CaseType.PETITE,
        'AAAAAAA',
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
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

      it('should create a ComputerCase', async () => {
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

      it('should not create a ComputerCase', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a ComputerCase', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            discount: 0.3,
            stock: 27,
            img: 'fsdf',
            brand: 'INTEL',
            type: 'BBBBBB',
            formats: 'BBBBBB',
            sizeMaxGpu: 1,
            sizeMaxVentirad: 1,
            sizeMaxPsu: 1,
            hardDriveSlots: 'BBBBBB',
            frontPanelOutputs: 'BBBBBB',
            fanIncluded: 'BBBBBB',
            fanSlotsAvailable: 'BBBBBB',
            watercoolingCompatibility: 'BBBBBB',
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

      it('should not update a ComputerCase', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a ComputerCase', async () => {
        const patchObject = Object.assign(
          {
            name: 'AAAAAA',
            price: 0.5,
            stock: 27,
            brand: 'INTEL',
            type: 'BBBBBB',
            frontPanelOutputs: 'BBBBBB',
            fanIncluded: 'BBBBBB',
            fanSlotsAvailable: 'BBBBBB',
            dimension: {
              height: 15,
              width: 15,
              length: 15,
            },
          },
          new ComputerCase()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a ComputerCase', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of ComputerCase', async () => {
        const returnedFromService = Object.assign(
          {
            type: 'BBBBBB',
            formats: 'BBBBBB',
            sizeMaxGpu: 1,
            sizeMaxVentirad: 1,
            sizeMaxPsu: 1,
            hardDriveSlots: 'BBBBBB',
            frontPanelOutputs: 'BBBBBB',
            fanIncluded: 'BBBBBB',
            fanSlotsAvailable: 'BBBBBB',
            watercoolingCompatibility: 'BBBBBB',
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

      it('should not return a list of ComputerCase', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a ComputerCase', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a ComputerCase', async () => {
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
