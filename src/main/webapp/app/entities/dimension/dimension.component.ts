import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDimension } from '@/shared/model/dimension.model';

import DimensionService from './dimension.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Dimension extends Vue {
  @Inject('dimensionService') private dimensionService: () => DimensionService;
  private removeId: number = null;

  public dimensions: IDimension[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDimensions();
  }

  public clear(): void {
    this.retrieveAllDimensions();
  }

  public retrieveAllDimensions(): void {
    this.isFetching = true;
    this.dimensionService()
      .retrieve()
      .then(
        res => {
          this.dimensions = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IDimension): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDimension(): void {
    this.dimensionService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.dimension.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDimensions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
