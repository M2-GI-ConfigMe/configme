import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IClientConfig } from '@/shared/model/client-config.model';

import ClientConfigService from './client-config.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ClientConfig extends Vue {
  @Inject('clientConfigService') private clientConfigService: () => ClientConfigService;
  private removeId: number = null;

  public clientConfigs: IClientConfig[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllClientConfigs();
  }

  public clear(): void {
    this.retrieveAllClientConfigs();
  }

  public retrieveAllClientConfigs(): void {
    this.isFetching = true;
    this.clientConfigService()
      .retrieve()
      .then(
        res => {
          this.clientConfigs = res.data;
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

  public prepareRemove(instance: IClientConfig): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeClientConfig(): void {
    this.clientConfigService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('configmeApp.clientConfig.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllClientConfigs();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
