package org.company.digivault.api;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.company.digivault.config.DigiVaultConstants;
import org.company.digivault.request.CreateAssetRequest;
import org.company.digivault.response.CreateAssetResponse;
import org.digivault.entity.Asset;
import org.digivault.services.AssetMetaService;

@Path(DigiVaultConstants.ASSET_BASE_API_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssetManagementService {

  private AssetMetaService assetMetaService;

  public AssetManagementService(AssetMetaService assetMetaService) {
    this.assetMetaService = assetMetaService;
  }

  @POST
  @UnitOfWork
  public Response createAsset(CreateAssetRequest createAssetRequest) {

    Asset asset = createAssetFromRequest(createAssetRequest);
    Asset createdAsset = assetMetaService.createAsset(asset);
    CreateAssetResponse createAssetResponse = generateCreateAssetResponse(createdAsset);

    return Response
            .ok(createAssetResponse)
            .build();
  }

  @GET
  @Path("/{assetId}")
  @UnitOfWork
  public Response getAssetById(@PathParam("assetId") Long assetId) {

    Asset asset = assetMetaService.getAssetById(assetId);

    if (asset == null) {
      return Response
              .status(Response.Status.NOT_FOUND)
              .build();
    }
    return Response
            .ok(asset)
            .build();
  }

  private Asset createAssetFromRequest(CreateAssetRequest createAssetRequest) {
    Asset asset = new Asset();
    asset.setUserId(createAssetRequest.getUserId());
    asset.setAccountHolderName(createAssetRequest.getAccountHolderName());
    asset.setAccountId(createAssetRequest.getAccountId());
    asset.setAmountInvested(createAssetRequest.getAmountInvested());
    asset.setNomineeReg(createAssetRequest.isNomineeReg());
    asset.setNomineeName(createAssetRequest.getNomineeName());
    asset.setType(createAssetRequest.getType());
    asset.setServiceProvider(createAssetRequest.getServiceProvider());
    asset.setDocumentId("Random_for_now");
    asset.setNotes(createAssetRequest.getNotes());
    return asset;
  }

  private CreateAssetResponse generateCreateAssetResponse(Asset createdAsset) {
     CreateAssetResponse createAssetResponse = new CreateAssetResponse();
     createAssetResponse.setAssetId(createdAsset.getId());
     return createAssetResponse;
  }

}
